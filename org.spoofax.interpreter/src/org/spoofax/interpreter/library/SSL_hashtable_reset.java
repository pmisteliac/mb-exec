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
import org.spoofax.interpreter.library.SSL_hashtable_create.Hashtable;
import org.spoofax.interpreter.terms.IStrategoInt;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class SSL_hashtable_reset extends AbstractPrimitive {

    protected SSL_hashtable_reset() {
        super("SSL_hashtable_reset", 0, 1);
    }

    @Override
    public boolean call(IContext env, List<IConstruct> svars, IStrategoTerm[] tvars)
            throws InterpreterException {
        if (!(Tools.isTermInt(tvars[0])))
            return false;

        SSLLibrary or = (SSLLibrary) env.getOperatorRegistry(SSLLibrary.REGISTRY_NAME);
        Hashtable ht = or.getHashtable(((IStrategoInt)tvars[0]).getValue());
        ht.clear();
        return true;
    }

}