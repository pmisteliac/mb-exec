/**
 * 
 */
package org.metaborg.meta.stratego.interpreter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import org.metaborg.meta.interpreter.framework.INodeSource;
import org.metaborg.meta.interpreter.framework.IValue;
import org.metaborg.meta.interpreter.framework.ImploderNodeSource;
import org.metaborg.meta.interpreter.framework.InterpreterException;
import org.spoofax.interpreter.core.StackTracer;
import org.spoofax.interpreter.library.IOperatorRegistry;
import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoConstructor;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;
import org.spoofax.jsglr.client.imploder.ImploderAttachment;
import org.spoofax.terms.TermFactory;
import org.spoofax.terms.io.binary.TermReader;

import com.github.krukow.clj_ds.PersistentMap;
import com.github.krukow.clj_lang.PersistentTreeMap;

import ds.generated.interpreter.F_0;
import ds.generated.interpreter.Generic_I_Module;
import ds.generated.interpreter.Generic_I_StrategyDef;
import ds.generated.interpreter.I_Module;
import ds.generated.interpreter.I_Strategy;
import ds.generated.interpreter.R_allocmodule_SEnv;
import ds.generated.interpreter.R_default_Value;
import ds.generated.interpreter.SDefT_4;
import ds.generated.interpreter.S_1;
import ds.generated.interpreter.Thunk_6;
import ds.generated.interpreter.allocModule_1;
import ds.manual.interpreter.AutoInterpInteropContext;
import ds.manual.interpreter.SBox;
import ds.manual.interpreter.SState;
import ds.manual.interpreter.VState;

/**
 * @author vladvergu
 *
 */
public class StrategoCoreInterpreter implements StrategoInterpreter {

	private ITermFactory termFactory;

	private SState strategyHeap;
	private PersistentMap<String, SBox> strategyEnv;

	private AutoInterpInteropContext interopContext;
	private IStrategoTerm currentTerm;

	public StrategoCoreInterpreter() {
		this(new TermFactory());
	}

	public StrategoCoreInterpreter(ITermFactory factory) {
		termFactory = factory;
		reset();
	}

	@Override
	public void reset() {
		interopContext = new AutoInterpInteropContext();
		interopContext.setFactory(termFactory);
		strategyHeap = new SState();
		strategyEnv = PersistentTreeMap.EMPTY;
		currentTerm = null;
	}

	@Override
	public void load(InputStream is) throws IOException {
		if (is == null)
			throw new IOException(
					"Could not load Stratego core input from null stream");

		load(new TermReader(termFactory).parseFromStream(is));
	}

	@Override
	public void load(Path p) throws IOException {
		load(new TermReader(termFactory).parseFromFile(p.toAbsolutePath()
				.toString()));
	}

	@Override
	public void load(IStrategoTerm ctree) throws IOException {
		INodeSource ctreeSource = ctree.getAttachment(ImploderAttachment.TYPE) != null ? new ImploderNodeSource(
				ctree.getAttachment(ImploderAttachment.TYPE)) : null;

		I_Module ctreeNode = (I_Module) new Generic_I_Module(ctreeSource, ctree)
				.specialize(1);

		R_allocmodule_SEnv sdefs_result = new allocModule_1(ctreeSource,
				ctreeNode).exec_allocmodule(PersistentTreeMap.EMPTY,
				strategyEnv, strategyHeap);
		strategyHeap = sdefs_result._1;
		strategyEnv = sdefs_result.value;

	}

	@Override
	public void addOperatorRegistry(IOperatorRegistry registry) {
		interopContext.addOperatorRegistry(registry);
	}

	@Override
	public void setCurrentTerm(IStrategoTerm term) {
		currentTerm = term;
	}

	@Override
	public IStrategoTerm getCurrentTerm() {
		return currentTerm;
	}

	@Override
	public ITermFactory getTermFactory() {
		return termFactory;
	}

	@Override
	public boolean invoke(String sname) {
		SBox thunkBox = strategyEnv.get(sname);
		if (thunkBox != null) {
			return evaluate(((Thunk_6) thunkBox.thunk).get_4());
		} else {
			throw new InterpreterException("Strategy " + sname
					+ " is not defined");
		}
	}

	@Override
	public boolean evaluate(IStrategoAppl sExpr) {
		final ITermFactory factory = getTermFactory();
		final IStrategoConstructor sdefT = factory.makeConstructor("SDefT", 4);
		final IStrategoConstructor exprConstr = sExpr.getConstructor();

		if (!exprConstr.getName().equals(sdefT.getName())
				|| exprConstr.getArity() != sdefT.getArity()) {
			sExpr = factory.makeAppl(sdefT, factory.makeString("dummy_0_0"),
					factory.makeList(), factory.makeList(), sExpr);
		}

		SDefT_4 sdef = (SDefT_4) new Generic_I_StrategyDef(null, sExpr)
				.specialize(2);

		return evaluate(sdef.get_4());
	}

	private boolean evaluate(I_Strategy sexpr) {
		try {
			R_default_Value result = sexpr.exec_default(interopContext,
					strategyEnv, PersistentTreeMap.EMPTY, getCurrentTerm(),
					getTermFactory(), strategyHeap, new VState(), false,
					interopContext.getStackTracer());
			strategyHeap = result._1;
			IValue value = result.value;
			StackTracer stacktracer = interopContext.getStackTracer();
			if (value.match(F_0.class) != null) {
				stacktracer.popOnFailure();
				return false;
			} else {
				stacktracer.popOnSuccess();
				setCurrentTerm(value.match(S_1.class).get_1());
				return true;
			}
		} catch (RuntimeException ex) {
			interopContext.getStackTracer().popOnExit(false);
			throw ex;
		}
	}
}
