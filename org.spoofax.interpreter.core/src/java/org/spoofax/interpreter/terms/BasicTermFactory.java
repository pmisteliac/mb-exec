/*
 * Created on 27. jan.. 2007
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.terms;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.spoofax.NotImplementedException;

public class BasicTermFactory implements ITermFactory {

    public static final IStrategoTerm[] EMPTY = new IStrategoTerm[0];
    private Map<String,Integer> ctorCache;

    public BasicTermFactory() {
        ctorCache = new WeakHashMap<String,Integer>();
    }
    
    public IStrategoTerm parseFromFile(String path) throws IOException {
        return parseFromStream(new FileInputStream(path));
    }

    public IStrategoTerm parseFromStream(InputStream inputStream) throws IOException {
        PushbackInputStream bis = new PushbackInputStream(inputStream);
        
        return parse(bis);
    }

    private IStrategoTerm parse(PushbackInputStream bis) throws IOException {
        final int ch = bis.read();
        switch(ch) {
        case '[': return parseList(bis);
        case '(': return parseTuple(bis);
        case '"': return parseString(bis);
        default:
            bis.unread(ch);
            if(Character.isLetter(ch)) {
                return parseAppl(bis);
            }
            else if(Character.isDigit(ch))
                return parseNumber(bis);
        }
        throw new ParseError("Invalid term : '" + (char)ch + "'");
    }

    private IStrategoTerm parseString(PushbackInputStream bis) throws IOException {
        StringBuffer sb = new StringBuffer();
        int ch = bis.read();
        if(ch == '"')
            return new BasicStrategoString("");
        boolean escaped = false;
        do {
            escaped = false;
            if(ch == '\\') {
                escaped = true;
                ch = bis.read();
            }
            if(escaped) {
                switch(ch) {
                case 'n':
                    sb.append('\n');
                    break;
                case 't':
                    sb.append('\t');
                    break;
                case 'b':
                    sb.append('\b');
                    break;
                case 'f':
                    sb.append('\f');
                    break;
                case 'r':
                    sb.append('\r');
                    break;
                case '\\':
                    sb.append('\\');
                    break;
                case '\'':
                    sb.append('\'');
                    break;
                case '\"':
                    sb.append('\"');
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    throw new NotImplementedException();
                default:
                    sb.append("\\" + (char)ch); 
                }
                ch = bis.read();
            } else if(ch != '\"') {
                sb.append((char)ch);
                ch = bis.read();
            }
        } while(escaped || ch != '\"');
        return new BasicStrategoString(sb.toString());
    }

    private IStrategoTerm parseAppl(PushbackInputStream bis) throws IOException {
        //System.err.println("appl");
        StringBuilder sb = new StringBuilder();
        int ch;
        
        ch = bis.read();
        do {
            sb.append((char)ch);
            ch = bis.read();
        } while(Character.isLetter(ch));
        
        //System.err.println(" - " + sb.toString());

        if(ch == '(') {
            List<IStrategoTerm> l = parseTermSequence(bis, ')');
            IStrategoConstructor c = makeConstructor(sb.toString(), l.size());
            return makeAppl(c, l.toArray(new IStrategoTerm[0]));
        } else {
            bis.unread(ch);
            IStrategoConstructor c = makeConstructor(sb.toString(), 0);
            return makeAppl(c, new IStrategoTerm[0]);
        }
    }

    private IStrategoTerm parseTuple(PushbackInputStream bis) throws IOException {
        //System.err.println("tuple");
        return makeTuple(parseTermSequence(bis, ')').toArray(new IStrategoTerm[0]));
    }

    private List<IStrategoTerm> parseTermSequence(PushbackInputStream bis, char endChar) throws IOException {
        //System.err.println("sequence");
        List<IStrategoTerm> els = new LinkedList<IStrategoTerm>();
        int ch = bis.read();
        if(ch == endChar)
            return els;
        bis.unread(ch);
        do {
            els.add(parse(bis));
            ch = bis.read();
        } while(ch == ',');
        
        if(ch != endChar)
            throw new ParseError("Sequence must end with '" + endChar + "',saw '" + (char)ch + "'");
        
        return els;
    }

    private IStrategoTerm parseList(PushbackInputStream bis) throws IOException {
        //System.err.println("list");
        return makeList(parseTermSequence(bis, ']'));
    }

    private IStrategoTerm parseNumber(PushbackInputStream bis) throws IOException {
        //System.err.println("number");
        String whole = parseDigitSequence(bis);
        
        int ch = bis.read();
        if(ch == '.') {
            String frac = parseDigitSequence(bis);
            ch = bis.read();
            if(ch == 'e' || ch == 'E') {
                String exp = parseDigitSequence(bis);
                double d = Double.parseDouble(whole + "." + frac + "e" + exp);
                return makeReal(d);
            }
            bis.unread(ch);
            double d = Double.parseDouble(whole + "." + frac);
            return makeReal(d);
        }
        bis.unread(ch);
        return makeInt(Integer.parseInt(whole));
    }

    private String parseDigitSequence(PushbackInputStream bis) throws IOException {
        StringBuilder sb = new StringBuilder();
        int ch = bis.read();
        do {
            sb.append((char)ch);
            ch = bis.read();
        } while(Character.isDigit(ch));
        bis.unread(ch);
        return sb.toString(); 
    }
    


    public IStrategoTerm parseFromString(String text) {
        try {
            return parseFromStream(new ByteArrayInputStream(text.getBytes()));
        } catch(IOException e) {
            return null;
        }
    }

    public IStrategoTerm replaceAppl(IStrategoConstructor constructor, IStrategoTerm[] kids,
            IStrategoTerm old) {
        return makeAppl(constructor, kids);
    }

    public void unparseToFile(IStrategoTerm t, OutputStream ous) throws IOException {
        ITermPrinter tp = new InlinePrinter();
        t.prettyPrint(tp);
        ous.write(tp.getString().getBytes());
    }

    public boolean hasConstructor(String ctorName, int arity) {
        return ctorCache.get(ctorName) != null;
    }

    public IStrategoAppl makeAppl(IStrategoConstructor ctr, IStrategoList kids) {
        return new BasicStrategoAppl(ctr, kids.getAllSubterms());
        
    }

    public IStrategoAppl makeAppl(IStrategoConstructor ctr, IStrategoTerm... terms) {
        return new BasicStrategoAppl(ctr, terms);
    }

    public IStrategoConstructor makeConstructor(String name, int arity) {
        ctorCache.put(name, arity);
        return new BasicStrategoConstructor(name, arity);
    }

    public IStrategoInt makeInt(int i) {
        return new BasicStrategoInt(i);
    }

    public IStrategoList makeList(IStrategoTerm... terms) {
        return new BasicStrategoList(terms);
    }

    public IStrategoList makeList(Collection<IStrategoTerm> terms) {
        return new BasicStrategoList(terms.toArray(new IStrategoTerm[0]));
    }

    public IStrategoReal makeReal(double d) {
        return new BasicStrategoReal(d);
    }

    public IStrategoString makeString(String s) {
        return new BasicStrategoString(s);
    }

    public IStrategoTuple makeTuple(IStrategoTerm... terms) {
        return new BasicStrategoTuple(terms);
    }

}