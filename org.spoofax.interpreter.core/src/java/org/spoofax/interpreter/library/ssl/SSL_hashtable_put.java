/*
 * Created on 08.aug.2005
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk near strategoxt.org>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.library.ssl;

import org.spoofax.interpreter.core.IContext;
import org.spoofax.interpreter.core.InterpreterException;
import org.spoofax.interpreter.library.AbstractPrimitive;
import org.spoofax.interpreter.stratego.Strategy;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class SSL_hashtable_put extends AbstractPrimitive {

    protected SSL_hashtable_put() {
        super("SSL_hashtable_put", 0, 3);
    }
    
    public boolean call(IContext env, Strategy[] sargs, IStrategoTerm[] targs) throws InterpreterException {
        if (!(targs[0] instanceof StrategoHashMap))
            return false;
        
        StrategoHashMap ath = (StrategoHashMap) targs[0];
        
        ath.put(targs[1], targs[2]);
        env.setCurrent(ath);
        return true;
    }
}
