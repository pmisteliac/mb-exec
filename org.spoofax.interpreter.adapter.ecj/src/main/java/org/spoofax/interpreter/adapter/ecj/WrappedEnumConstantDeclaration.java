/*
 * Created on 2. okt.. 2006
 *
 * Copyright (c) 2005-2011, Karl Trygve Kalleberg <karltk near strategoxt dot org>
 * 
 * Licensed under the GNU Lesser Public License, v2.1
 */
package org.spoofax.interpreter.adapter.ecj;

import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.spoofax.interpreter.terms.IStrategoConstructor;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class WrappedEnumConstantDeclaration extends WrappedBodyDeclaration {
    
    private static final long serialVersionUID = 1L;

    private final EnumConstantDeclaration wrappee;
    private final static IStrategoConstructor CTOR = new ASTCtor("EnumConstantDeclaration", 4);
    
    WrappedEnumConstantDeclaration(EnumConstantDeclaration wrappee) {
        super(CTOR);
        this.wrappee = wrappee;
    }
    
    @Override
    public IStrategoTerm getSubterm(int index) {
        switch(index) {
        case 0:
            return ECJFactory.wrap(wrappee.modifiers());
        case 1:
            return ECJFactory.wrap(wrappee.getName());
        case 2:
            return ECJFactory.wrap(wrappee.arguments());
        case 3:
            return ECJFactory.wrap(wrappee.getAnonymousClassDeclaration());
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public EnumConstantDeclaration getWrappee() {
        return wrappee;
    }

}
