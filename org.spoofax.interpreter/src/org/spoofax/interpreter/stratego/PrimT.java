/*
 * Created on 07.aug.2005
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.stratego;

import java.util.ArrayList;
import java.util.List;

import org.spoofax.DebugUtil;
import org.spoofax.interpreter.EvaluationStack;
import org.spoofax.interpreter.IConstruct;
import org.spoofax.interpreter.IContext;
import org.spoofax.interpreter.InterpreterException;
import org.spoofax.interpreter.Tools;
import org.spoofax.interpreter.library.AbstractPrimitive;
import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class PrimT extends Strategy {

    protected String name;

    protected List<IConstruct> svars;

    protected IStrategoTerm[] tvars;

    public PrimT(String name, List<Strategy> svars, List<IStrategoTerm> tvars) {
        this.name = name;
        this.svars = new ArrayList<IConstruct>();
        for(Strategy s : svars)
            this.svars.add(s);
        this.tvars = tvars.toArray(new IStrategoTerm[0]);
    }

    public boolean eval(IContext env, EvaluationStack es) throws InterpreterException {

        if (DebugUtil.isDebugging()) {
            debug("PrimT.eval() - ", env.current());
        }

        
        AbstractPrimitive prim = env.lookupOperator(name);

        if (prim == null)
            throw new InterpreterException("No such function : '" + name + "'");

        IStrategoTerm[] vals = new IStrategoTerm[tvars.length];
        for(int i = 0; i < tvars.length; i++) {
            // FIXME this cast should be moved out
            IStrategoAppl t = (IStrategoAppl)tvars[i];
            vals[i] = env.lookupVar(Tools.javaStringAt(t, 0));
        }


        if (vals.length != prim.getTArity())
            throw new InterpreterException("Wrong term arity when calling '" + name + "', expected " + prim.getTArity() + " got " + vals.length);

        if (svars.size() != prim.getSArity())
            throw new InterpreterException("Wrong strategy arity when calling '" + name + "', expected " + prim.getSArity() + " got " + svars.size());

        if(DebugUtil.isDebugging()) {
            CallT.printStrategyCall(name, null, svars, null, vals);
        }
        boolean r = prim.call(env, svars, vals);

        return DebugUtil.traceReturn(r, env.current(), this);
    }

    public void prettyPrint(StupidFormatter sf) {
        sf.append("PrimT(\"" + name + "\")");
    }

}
