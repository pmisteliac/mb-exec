/*
 * Created on 2. okt.. 2006
 *
 * Copyright (c) 2005-2011, Karl Trygve Kalleberg <karltk near strategoxt dot org>
 * 
 * Licensed under the GNU Lesser Public License, v2.1
 */
package org.spoofax.interpreter.adapter.ecj;

import org.eclipse.jdt.core.dom.LineComment;
import org.spoofax.interpreter.terms.IStrategoConstructor;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class WrappedLineComment extends WrappedComment {

    private static final long serialVersionUID = 1L;

    // FIXME where is the content?
    private final LineComment wrappee;
    private final static IStrategoConstructor CTOR = new ECJConstructor("LineComment", 0);
    
    WrappedLineComment(LineComment wrappee) {
        super(CTOR);
        this.wrappee = wrappee;
    }
    
    @Override
    public IStrategoTerm getSubterm(int index) {
	
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public LineComment getWrappee() {
        return wrappee;
    }

}
