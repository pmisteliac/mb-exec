package org.spoofax.interpreter.library.ssl;

import io.usethesource.capsule.Set;

import org.spoofax.interpreter.core.IContext;
import org.spoofax.interpreter.library.AbstractPrimitive;
import org.spoofax.interpreter.stratego.Strategy;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class SSL_immutable_relation_values_set extends AbstractPrimitive {

    protected SSL_immutable_relation_values_set() {
        super("SSL_immutable_relation_values", 0, 0);
    }

    @Override
    public boolean call(IContext env, Strategy[] sargs, IStrategoTerm[] targs) {
        if(!(env.current() instanceof StrategoImmutableRelation)) {
            return false;
        }

        final StrategoImmutableRelation map = (StrategoImmutableRelation) env.current();
        final Set.Transient<IStrategoTerm> result = Set.Transient.of();
        for(IStrategoTerm value : map.backingRelation.inverse().keySet()) {
            result.__insert(value);
        }

        env.setCurrent(new StrategoImmutableSet(result.freeze()));
        return true;
    }
}
