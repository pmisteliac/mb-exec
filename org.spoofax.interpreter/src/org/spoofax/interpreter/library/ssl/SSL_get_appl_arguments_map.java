/*
 * Created on 9. jan.. 2007
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.library.ssl;

import java.util.List;

import org.spoofax.interpreter.IConstruct;
import org.spoofax.interpreter.IContext;
import org.spoofax.interpreter.InterpreterException;
import org.spoofax.interpreter.Tools;
import org.spoofax.interpreter.library.AbstractPrimitive;
import org.spoofax.interpreter.stratego.CallT;
import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class SSL_get_appl_arguments_map extends AbstractPrimitive {

    SSL_get_appl_arguments_map() {
        super("SSL_get_appl_arguments_map", 1, 1);
    }
    
    @Override
    public boolean call(IContext env, List<IConstruct> svars, IStrategoTerm[] tvars)
            throws InterpreterException {
        
        if(!Tools.isTermAppl(tvars[0]))
            return false;
        
        IStrategoAppl a = (IStrategoAppl) tvars[0];
        IConstruct c = svars.get(0);
        final int arity = a.getConstructor().getArity();
        IStrategoTerm[] result = new IStrategoTerm[arity];
        for(int i = 0; i < arity; i++) {
            env.setCurrent(a.getSubterm(i));
            if(!CallT.callHelper(c, env))
                return false;
            result[i] = env.current();
        }
        env.setCurrent(env.getFactory().makeList(result));
        return true;
    }

}
