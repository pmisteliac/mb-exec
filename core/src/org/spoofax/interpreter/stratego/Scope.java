/*
 * Created on 07.aug.2005
 *
 * Copyright (c) 2004, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.stratego;

import java.util.List;

import org.spoofax.interpreter.FatalError;
import org.spoofax.interpreter.IContext;
import org.spoofax.interpreter.VarScope;

public class Scope extends Strategy {

    protected List<String> vars;
    protected Strategy body;
    
    public Scope(List<String> vars, Strategy body) {
        this.vars = vars;
        this.body = body;
    }

    public boolean eval(IContext env) throws FatalError {
        debug("Scope.eval() - " + env.current());
        StringBuffer sb = new StringBuffer();
        
        VarScope oldScope = env.getVarScope();
        // oldScope.dump("o - ");
        
        VarScope newScope = new VarScope(oldScope);
        for(String s : vars) {
            newScope.add(s, null);
            sb.append(s + ",");
        }
        debug(" vars : [" + sb.toString() + "]");
        env.setVarScope(newScope);
        debug("" + env.getVarScope());
        // env.getVarScope().dump("n - ");
        
        boolean r = body.eval(env);
        env.setVarScope(oldScope);
        debug("<scope, dropped : [" + sb.toString() + "]");
        return r;
    }

}
