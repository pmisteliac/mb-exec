/*
 * Created on 07.aug.2005
 *
 * Copyright (c) 2004, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.stratego;

import org.spoofax.DebugUtil;
import org.spoofax.interpreter.IConstruct;
import org.spoofax.interpreter.IContext;
import org.spoofax.interpreter.InterpreterException;

public class Id extends Strategy {

    public IConstruct eval(IContext e) throws InterpreterException {

        if (DebugUtil.isDebugging()) {
            debug("Id.eval() - ", e.current());
        }

        return getHook().pop().onSuccess(e);
    }

    public void prettyPrint(StupidFormatter sf) {
        sf.append("Id");
    }
}