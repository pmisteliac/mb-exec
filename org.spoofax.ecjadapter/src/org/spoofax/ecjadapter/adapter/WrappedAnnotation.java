/*
 * Created on 25. des.. 2006
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.ecjadapter.adapter;

import org.eclipse.jdt.core.dom.Annotation;
import org.spoofax.interpreter.terms.IStrategoConstructor;

public abstract class WrappedAnnotation extends WrappedExpression implements IWrappedExtendedModifier {

    protected WrappedAnnotation(IStrategoConstructor constructor) {
        super(constructor);
    }

    @Override
    public abstract Annotation getWrappee();
}
