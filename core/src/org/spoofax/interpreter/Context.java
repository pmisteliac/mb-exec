/*
 * Created on 07.aug.2005
 *
 * Copyright (c) 2004, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the IBM Common Public License, v1.0
 */
package org.spoofax.interpreter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.spoofax.interpreter.stratego.Build;
import org.spoofax.interpreter.stratego.CallT;
import org.spoofax.interpreter.stratego.Fail;
import org.spoofax.interpreter.stratego.GuardedLChoice;
import org.spoofax.interpreter.stratego.Id;
import org.spoofax.interpreter.stratego.Let;
import org.spoofax.interpreter.stratego.Match;
import org.spoofax.interpreter.stratego.OpDecl;
import org.spoofax.interpreter.stratego.PrimT;
import org.spoofax.interpreter.stratego.SDefT;
import org.spoofax.interpreter.stratego.Scope;
import org.spoofax.interpreter.stratego.Seq;
import org.spoofax.interpreter.stratego.Strategy;

import aterm.ATerm;
import aterm.ATermAppl;
import aterm.ATermList;
import aterm.pure.PureFactory;

public class Context extends ATermed implements IContext {

    protected ATerm current;

    private Map<String, OpDecl> opdecls;
    
    protected VarScope varScope;

    public Context() {
        super();
        
        opdecls = new HashMap<String, OpDecl>();
        varScope = new VarScope(null);
    }

    public ATerm current() {
        return current;
    }

    public void setCurrent(ATerm term) {
        current = term;
    }


    public static void debug(String s) {
        System.out.println(s);
    }

    public boolean invoke(String name, Object object, Object object2)
            throws FatalError {
        SDefT s = lookupSVar(name);
        return s.getBody().eval(this);
    }

    public ATerm lookupVar(String n) throws FatalError {
        return varScope.lookup(n);
    }

    public SDefT lookupSVar(String n) throws FatalError {
        return varScope.lookupSVar(n);
    }

    public PureFactory getFactory() {
        return factory;
    }

    public boolean bindVars(List<Pair<String, ATerm>> r) {
        for (Pair<String, ATerm> x : r) {
            VarScope s = varScope.scopeOf(x.first);
            if (s == null) {
                varScope.add(x.first, x.second);
            } else if (s.hasVarInLocalScope(x.first)) {
                ATerm t = s.lookup(x.first);
                boolean eq = t.match(x.second) != null;
                if (!eq) {
                    debug(x.first + " already bound to " + t + ", new: "
                            + x.second);
                    return eq;
                }
            } else {
                s.add(x.first, x.second);
            }
        }
        return true;
    }

    public void dump(String prefix) {
        varScope.dump(prefix);
    }

    public VarScope getVarScope() {
        return varScope;
    }

    public void setVarScope(VarScope newVarScope) {
        varScope = newVarScope;
    }

    void addOpDecl(String name, OpDecl decl) {
        opdecls.put(name, decl);
    }

    public void addSVar(String name, SDefT def) {
        varScope.addSVar(name, def);
    }
}
