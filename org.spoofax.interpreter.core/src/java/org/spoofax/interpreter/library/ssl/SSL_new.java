/*
 * Created on 08.aug.2005
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.library.ssl;

import org.spoofax.interpreter.core.IContext;
import org.spoofax.interpreter.core.InterpreterException;
import org.spoofax.interpreter.library.AbstractPrimitive;
import org.spoofax.interpreter.stratego.Strategy;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;

public class SSL_new extends AbstractPrimitive {

    private int alphaCounter;
    private int counter;
    private int letterA = 'a';

    protected SSL_new() {
        super("SSL_new", 0, 0);

        alphaCounter = 0;
        counter = 0;
    }

    public boolean call(IContext env, Strategy[] sargs, IStrategoTerm[] targs) throws InterpreterException {

        ITermFactory factory = env.getFactory();

        String s = (char)(letterA + alphaCounter) + "_" + counter;
        while(factory.hasConstructor(s, 0)) {
            alphaCounter++;
            if(alphaCounter > 25) {
                alphaCounter = 0;
                counter++;
            }
            s = (char)(letterA + alphaCounter) + "_" + counter;
        }

        env.setCurrent(env.getFactory().makeString(s));
        return true;
    }
}