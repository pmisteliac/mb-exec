/*
 * Created on 28. feb.. 2007
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.adapter.ecj;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.spoofax.interpreter.terms.IStrategoConstructor;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class WrappedIType extends WrappedECJNode {

    private final IType wrappee;
    private final static IStrategoConstructor CTOR = new ASTCtor("IType", 5);

    WrappedIType(IType wrappee) {
        super(CTOR);
        this.wrappee = wrappee;
    }

    @Override
    public IStrategoTerm getSubterm(int index) {
        switch(index) {
        case 0:
            return ECJFactory.wrap(wrappee.getElementName());
        case 1:
            return ECJFactory.wrap(wrappee.hashCode());
        case 2:
            return ECJFactory.wrap(wrappee.getDeclaringType());
        case 3:
            try {
                return ECJFactory.wrap(wrappee.getSuperclassName());
            } catch(JavaModelException e) {
                e.printStackTrace();
                return None.INSTANCE;
            }
        case 4:
            try {
                return ECJFactory.wrap(wrappee.getSuperInterfaceTypeSignatures());
            } catch(JavaModelException e) {
                e.printStackTrace();
                return None.INSTANCE;
            }
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    
    @Override
    public IType getWrappee() {
        return wrappee;
    }

}