/*
 * Created on 08.aug.2005
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk near strategoxt.org>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.library.ssl;

import java.util.LinkedHashMap;

import org.spoofax.interpreter.core.IContext;
import org.spoofax.interpreter.core.InterpreterException;
import org.spoofax.interpreter.core.Tools;
import org.spoofax.interpreter.library.AbstractPrimitive;
import org.spoofax.interpreter.stratego.Strategy;
import org.spoofax.interpreter.terms.IStrategoInt;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class SSL_hashtable_create extends AbstractPrimitive {

    private final SSLLibrary library;

    protected static class Hashtable extends LinkedHashMap<IStrategoTerm, IStrategoTerm> {

        private static final long serialVersionUID = -8193582031891397734L;

        public Hashtable(int initialSize, int maxLoad) {
            super(initialSize, 1.0f * maxLoad / 100);
        }

    }

    protected SSL_hashtable_create(SSLLibrary library) {
        super("SSL_hashtable_create", 0, 2);
        this.library = library;
    }

    public boolean call(IContext env, Strategy[] sargs, IStrategoTerm[] targs)
            throws InterpreterException {

        if (!(Tools.isTermInt(targs[0])))
            return false;
        if (!(Tools.isTermInt(targs[1])))
            return false;

        int initialSize = ((IStrategoInt)targs[0]).intValue();
        int maxLoad = ((IStrategoInt)targs[1]).intValue();

        int ref = library.registerHashtable(new Hashtable(initialSize, maxLoad));
        
        env.setCurrent(env.getFactory().makeInt(ref));
        return true;
    }
}
