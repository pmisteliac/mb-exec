/*
 * Created on 2. okt.. 2006
 *
 * Copyright (c) 2005-2011, Karl Trygve Kalleberg <karltk near strategoxt dot org>
 * 
 * Licensed under the GNU Lesser Public License, v2.1
 */
package org.spoofax.interpreter.adapter.ecj;

import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.spoofax.interpreter.terms.IStrategoConstructor;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class WrappedVariableDeclarationExpression extends WrappedExpression {

    private static final long serialVersionUID = 1L;

    private final VariableDeclarationExpression wrappee;
    private final static IStrategoConstructor CTOR = new ASTCtor("VariableDeclarationExpression", 3);

    WrappedVariableDeclarationExpression(VariableDeclarationExpression wrappee) {
        super(CTOR);
        this.wrappee = wrappee;
    }
    
    @Override
    public IStrategoTerm getSubterm(int index) {
        switch(index) {
        case 0:
            return ECJFactory.wrap(wrappee.modifiers());
        case 1:
            return ECJFactory.wrapType(wrappee.getType());
        case 2:
            return ECJFactory.wrap(wrappee.fragments());
        }
        
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public VariableDeclarationExpression getWrappee() {
        return wrappee;
    }

}
