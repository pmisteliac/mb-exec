/*
 * Created on 2. okt.. 2006
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.adapters.ecj;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.PrimitiveType.Code;
import org.spoofax.NotImplementedException;
import org.spoofax.interpreter.terms.IStrategoConstructor;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class WrappedPrimitiveType extends WrappedAppl {

    // FIXME should we even keep this?
    
    private final PrimitiveType wrappee;
    private final static IStrategoConstructor CTOR = new ASTCtor("PrimitiveType", 1);
    
    WrappedPrimitiveType(PrimitiveType wrappee) {
        super(CTOR);
        this.wrappee = wrappee;
    }
    
    @Override
    public IStrategoTerm getSubterm(int index) {
        if(index == 0) {
            Code code = wrappee.getPrimitiveTypeCode();
            if(code == PrimitiveType.DOUBLE)
                return WrappedECJFactory.wrap("double");
            if(code == PrimitiveType.FLOAT)
                return WrappedECJFactory.wrap("float");
            if(code == PrimitiveType.INT)
                return WrappedECJFactory.wrap("int");
            if(code == PrimitiveType.BYTE)
                return WrappedECJFactory.wrap("byte");
            if(code == PrimitiveType.BOOLEAN)
                return WrappedECJFactory.wrap("boolean");
            if(code == PrimitiveType.CHAR)
                return WrappedECJFactory.wrap("char");
            if(code == PrimitiveType.LONG)
                return WrappedECJFactory.wrap("long");
            if(code == PrimitiveType.SHORT)
                return WrappedECJFactory.wrap("short");
            if(code == PrimitiveType.VOID)
                return WrappedECJFactory.wrap("void");
            else
                throw new NotImplementedException("Unknown primitive type: " + code.getClass() + " " + code.toString());
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public ASTNode getWrappee() {
        return wrappee;
    }
}