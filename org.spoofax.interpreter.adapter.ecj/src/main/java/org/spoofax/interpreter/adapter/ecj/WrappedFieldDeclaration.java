/*
 * Created on 2. okt.. 2006
 *
 * Copyright (c) 2005-2011, Karl Trygve Kalleberg <karltk near strategoxt dot org>
 * 
 * Licensed under the GNU Lesser Public License, v2.1
 */
package org.spoofax.interpreter.adapter.ecj;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.spoofax.interpreter.terms.IStrategoConstructor;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class WrappedFieldDeclaration extends WrappedBodyDeclaration {
    
    private static final long serialVersionUID = 1L;

    private final FieldDeclaration wrappee;
    private final static IStrategoConstructor CTOR = new ECJConstructor("FieldDeclaration", 4);
    
    WrappedFieldDeclaration(FieldDeclaration wrappee) {
        super(CTOR);
        this.wrappee = wrappee;
    }
    
    @Override
    public IStrategoTerm getSubterm(int index) {
        switch(index) {
        case 0:
            return ECJFactory.wrap(wrappee.getJavadoc());
        case 1:
            return ECJFactory.wrap(wrappee.modifiers());
        case 2:
            return ECJFactory.wrapType(wrappee.getType());
        case 3:
            return ECJFactory.wrap(wrappee.fragments());
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public FieldDeclaration getWrappee() {
        return wrappee;
    }

}
