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
import org.spoofax.interpreter.core.Tools;
import org.spoofax.interpreter.library.AbstractPrimitive;
import org.spoofax.interpreter.stratego.Strategy;
import org.spoofax.interpreter.terms.IStrategoInt;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class SSL_indexedSet_create extends AbstractPrimitive {

    protected SSL_indexedSet_create() {
        super("SSL_indexedSet_create", 0, 2);
    }
    
    public boolean call(IContext env, Strategy[] sargs, IStrategoTerm[] targs) throws InterpreterException {

        if(!(Tools.isTermInt(targs[0])))
            return false;
        if(!(Tools.isTermInt(targs[1])))
            return false;

        int initialSize = ((IStrategoInt)targs[0]).intValue();
        int maxLoad = ((IStrategoInt)targs[1]).intValue();

        IStrategoTerm result = new IndexedSet(initialSize, maxLoad);
        env.setCurrent(result);
        
        return true;
    }
}
