/*
 * Created on 08.aug.2005
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.library;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.spoofax.interpreter.FatalError;
import org.spoofax.interpreter.IContext;
import org.spoofax.interpreter.Tools;
import org.spoofax.interpreter.stratego.Strategy;

import aterm.ATerm;
import aterm.ATermInt;

public class SSL_indexedSet_create extends Primitive {

    class ATermIndexedSet  {
        
        private static final long serialVersionUID = -4514696890481283123L;
        private int counter;
        Map<Integer, ATerm> map;
        
        ATermIndexedSet(int initialSize, int maxLoad) {
            map = new HashMap<Integer, ATerm>(initialSize, 1.0f*maxLoad/100);
            counter = 0;
        }
        
        public int put(ATerm value) {
            int idx = counter++; 
            map.put(idx, value);
            return idx;
        }

        public int getIndex(ATerm t) {
            Set<Map.Entry<Integer,ATerm>> entries = map.entrySet();
            
            for(Map.Entry<Integer,ATerm> x : entries) {
                if(x.getValue() == t)
                    return x.getKey();
            }
            
            return -1;
        }

        public boolean containsValue(ATerm t) {
            return map.containsValue(t);
        }

        public Collection<ATerm> values() {
            return map.values();
        }

        public boolean remove(ATerm t) {
            int idx = getIndex(t);

            if(idx == -1)
                return false;
            map.remove(idx);
            return true;
        }

        public void clear() {
            map.clear();
        }
    }

    // FIXME: Must have state per-interpreter, not per-JVM
    protected static Map<Integer, ATermIndexedSet> map = new HashMap<Integer, ATermIndexedSet>();;
    protected static int setCounter = 0;
    
    protected SSL_indexedSet_create() {
        super("SSL_indexedSet_create", 0, 2);
    }
    
    public boolean call(IContext env, List<Strategy> sargs, List<ATerm> targs) throws FatalError {
        debug("SSL_indexedSet_create");
        
        if(!Tools.isATermInt(targs.get(0)))
            return false;
        if(!Tools.isATermInt(targs.get(1)))
            return false;
        
        int initialSize = Tools.getATermInt((ATermInt)targs.get(0));
        int maxLoad = Tools.getATermInt((ATermInt)targs.get(1));
        
        ATermIndexedSet is = new ATermIndexedSet(initialSize, maxLoad);
        int n = setCounter++;
        map.put(n, is);
        
        env.setCurrent(env.makeTerm(n));
        return true;
    }
}
