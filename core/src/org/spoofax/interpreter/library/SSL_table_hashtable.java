/*
 * Created on 08.aug.2005
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.spoofax.interpreter.FatalError;
import org.spoofax.interpreter.IContext;
import org.spoofax.interpreter.stratego.Strategy;

import aterm.ATerm;

public class SSL_table_hashtable extends Primitive {

    protected SSL_table_hashtable() {
        super("SSL_table_hashtable", 0, 0);
    }
    
    // FIXME: Must be per-interpreter instance, not per-JVM instance
    protected static Map<ATerm,ATerm> map = new HashMap<ATerm,ATerm>(100, 0.80f);
    
    public boolean call(IContext env, List<Strategy> sargs, List<ATerm> targs) throws FatalError {
        debug("SSL_table_hashtable");
        
        env.setCurrent(env.makeTerm(0xDEADBEEF));
        return true;
    }
}
