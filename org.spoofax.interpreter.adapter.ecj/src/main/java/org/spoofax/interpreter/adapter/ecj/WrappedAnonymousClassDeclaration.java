/*
 * Created on 2. okt.. 2006
 *
 * Copyright (c) 2005-2011, Karl Trygve Kalleberg <karltk near strategoxt dot org>
 * 
 * Licensed under the GNU Lesser Public License, v2.1
 */
package org.spoofax.interpreter.adapter.ecj;

import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.spoofax.interpreter.terms.IStrategoConstructor;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class WrappedAnonymousClassDeclaration extends WrappedASTNode {
    
    private static final long serialVersionUID = 1L;

    private final AnonymousClassDeclaration wrappee;
    private final static IStrategoConstructor CTOR = new ECJConstructor("AnonymousClassDeclaration", 1);
    
    WrappedAnonymousClassDeclaration(AnonymousClassDeclaration wrappee) {
        super(CTOR);
        this.wrappee = wrappee;
    }
    
    @Override
    public IStrategoTerm getSubterm(int index) {
        if(index == 0)
            return ECJFactory.wrap(wrappee.bodyDeclarations());
        
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public AnonymousClassDeclaration getWrappee() {
        return wrappee;
    }

}
