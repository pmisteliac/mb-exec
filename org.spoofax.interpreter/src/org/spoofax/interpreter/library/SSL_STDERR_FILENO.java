/*
 * Created on 9. jan.. 2007
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.library;

import java.util.List;

import org.spoofax.interpreter.IConstruct;
import org.spoofax.interpreter.IContext;
import org.spoofax.interpreter.InterpreterException;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class SSL_STDERR_FILENO extends AbstractPrimitive {

    public SSL_STDERR_FILENO() {
        super("SSL_STDERR_FILENO", 0, 0);
    }
    
    @Override
    public boolean call(IContext env, List<IConstruct> svars, IStrategoTerm[] tvars)
            throws InterpreterException {
        env.setCurrent(env.getFactory().makeInt(SSLLibrary.CONST_STDERR));
        return true;
    }

}