/*
 * Created on 08.aug.2005
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.library;

import java.util.List;

import org.spoofax.interpreter.IContext;
import org.spoofax.interpreter.InterpreterException;
import org.spoofax.interpreter.Tools;
import org.spoofax.interpreter.stratego.Strategy;
import org.spoofax.interpreter.terms.IStrategoInt;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class SSL_gti extends Primitive {

    protected SSL_gti() {
        super("SSL_gti", 0, 2);
    }
    
    public boolean call(IContext env, List<Strategy> sargs, IStrategoTerm[] targs) throws InterpreterException {
        
        if(!Tools.isTermInt(targs[0]))
            return false;
        if(!Tools.isTermInt(targs[1]))
            return false;

        IStrategoInt a = (IStrategoInt) targs[0];
        IStrategoInt b = (IStrategoInt) targs[1];
        return a.getValue() > b.getValue();
    }
}
