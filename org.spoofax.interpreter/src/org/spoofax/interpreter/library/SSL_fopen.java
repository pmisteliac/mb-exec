/*
 * Created on 11. jan.. 2007
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
import org.spoofax.interpreter.Tools;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class SSL_fopen extends AbstractPrimitive {

    SSL_fopen() {
        super("SSL_fopen", 0, 2);
    }
    
    @Override
    public boolean call(IContext env, List<IConstruct> svars, IStrategoTerm[] tvars)
            throws InterpreterException {
        
        if(!Tools.isTermString(tvars[0]))
            return false;
        if(!Tools.isTermString(tvars[1]))
            return false;
        
        String fn = Tools.javaString(tvars[0]);
        String mode = Tools.javaString(tvars[1]);
        
        SSLLibrary op = (SSLLibrary) env.getOperatorRegistry(SSLLibrary.REGISTRY_NAME);
        int ref = op.openRandomAccessFile(fn, mode);
        env.setCurrent(env.getFactory().makeInt(ref));
        return true;
    }

}
