package org.spoofax.interpreter.library.ssl;

import io.usethesource.capsule.BinaryRelation;

import org.spoofax.interpreter.core.IContext;
import org.spoofax.interpreter.library.AbstractPrimitive;
import org.spoofax.interpreter.stratego.Strategy;
import org.spoofax.interpreter.terms.IStrategoTerm;
import java.util.Map;

public class SSL_immutable_relation_subtract extends AbstractPrimitive {

    protected SSL_immutable_relation_subtract() {
        super("SSL_immutable_relation_subtract", 0, 1);
    }

    @Override
    public boolean call(IContext env, Strategy[] sargs, IStrategoTerm[] targs) {
        if(!(env.current() instanceof StrategoImmutableRelation)) {
            return false;
        }
        if(!(targs[0] instanceof StrategoImmutableRelation)) {
            return false;
        }

        final BinaryRelation.Immutable<IStrategoTerm, IStrategoTerm> left =
            ((StrategoImmutableRelation) env.current()).backingRelation;
        final BinaryRelation.Immutable<IStrategoTerm, IStrategoTerm> right =
            ((StrategoImmutableRelation) targs[0]).backingRelation;

        env.setCurrent(new StrategoImmutableRelation(subtract(left, right)));
        return true;
    }

    public static BinaryRelation.Immutable<IStrategoTerm, IStrategoTerm> subtract(
        BinaryRelation.Immutable<IStrategoTerm, IStrategoTerm> left,
        BinaryRelation.Immutable<IStrategoTerm, IStrategoTerm> right) {
        final BinaryRelation.Transient<IStrategoTerm, IStrategoTerm> result = left.asTransient();
        for(Map.Entry<IStrategoTerm, IStrategoTerm> e : right.entrySet()) {
            result.__remove(e.getKey(), e.getValue());
        }

        return result.freeze();
    }

}