/*
 * Created on 17.jul.2005
 *
 * Copyright (c) 2004, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the IBM Common Public License, v1.0
 */
package org.spoofax.interp;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import aterm.ATerm;
import aterm.ATermAppl;
import aterm.ATermInt;
import aterm.ATermList;
import aterm.ATermReal;

public class Library {

    private static final Class[] defaultSignature = new Class[] {
            Interpreter.class, ATermList.class, ATermList.class };

    private static class MethodEntry {
        MethodEntry(String name, int svarArity, int tvarArity) {
            this.name = name;
            this.svarArity = svarArity;
            this.tvarArity = tvarArity;
        }

        String name;

        int svarArity;

        int tvarArity;
    }

    private static final MethodEntry[] library = new MethodEntry[] {
            new MethodEntry("_id", 0, 0), 
            new MethodEntry("_fail", 0, 0),
            new MethodEntry("SSL_addi", 0, 2),
            new MethodEntry("SSL_addr", 0, 2),
            new MethodEntry("SSL_printnl", 0, 2)
    };
    private static Object hashtable;

    public static List<ExtStrategy> getStrategies() {
        List<ExtStrategy> r = new ArrayList<ExtStrategy>(library.length);
        for (int i = 0; i < library.length; i++) {
            r.add(new ExtStrategy(library[i].name, library[i].svarArity,
                                  library[i].tvarArity, 
                                  lookup(library[i].name,
                                         library[i].svarArity,
                                         library[i].tvarArity)));
        }
        return r;
    }

    public static Method lookup(String name, int tvarArity, int svarArity) {
        try {
            return Library.class.getMethod(name, defaultSignature);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static boolean _id(Interpreter itp, ATermList svars, ATermList tvars) {
        return true;
    }

    public static boolean _fail(Interpreter itp, ATermList svars, ATermList tvars) {
        return false;
    }

    public static boolean SSL_addi(Interpreter itp, ATermList svars, ATermList tvars) {
        if(Tools.termAt(tvars, 0).getType() != ATerm.INT)
            return false;
        if(Tools.termAt(tvars, 1).getType() != ATerm.INT)
            return false;

        ATermInt a = Tools.intAt(tvars, 0);
        ATermInt b = Tools.intAt(tvars, 1);
        itp.setCurrent(itp.makeTerm(a.getInt() + b.getInt()));
        return true;
    }

    public static boolean SSL_addr(Interpreter itp, ATermList svars, ATermList tvars) {
        if(Tools.termAt(tvars, 0).getType() != ATerm.REAL)
            return false;
        if(Tools.termAt(tvars, 1).getType() != ATerm.REAL)
            return false;
        
        ATermReal a = Tools.realAt(tvars, 0);
        ATermReal b = Tools.realAt(tvars, 1);
        itp.setCurrent(itp.makeTerm(a.getReal() + b.getReal()));
        return true;
    }

    public static boolean SSL_printnl(Interpreter itp, ATermList svars, ATermList tvars) {
        ATerm file = Tools.termAt(tvars, 0);
        ATerm term = Tools.termAt(tvars, 1);
        
        // FIXME: Use different files (stdout, stderr, ...)
        System.out.println("" + term);
        return true;
    }
    
    public static boolean SSL_table_hastable(Interpreter itp, ATermList svars, ATermList tvars) {
        if(hashtable == null) {
            hashtable = new HashMap(); 
        }
        itp.setCurrent(itp.makeTerm(10000000));
        return true;
    }
}