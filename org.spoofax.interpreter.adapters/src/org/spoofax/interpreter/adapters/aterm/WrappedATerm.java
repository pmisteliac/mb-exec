/*
 * Created on 15. sep.. 2006
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.adapters.aterm;

import org.spoofax.NotImplementedException;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.PrettyPrinter;

import aterm.ATerm;

public abstract class WrappedATerm implements IStrategoTerm {

    protected boolean slowCompare(Object second) {
        throw new WrapperException("Cannot compare with class " + second.getClass());
    }
    
    abstract ATerm getATerm();
    
    public String prettyPrint() {
         throw new NotImplementedException();
    }
    
    public void prettyPrint(PrettyPrinter pp) {
         throw new NotImplementedException();        
    }
}