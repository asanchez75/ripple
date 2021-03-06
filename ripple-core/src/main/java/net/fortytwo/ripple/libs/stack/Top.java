package net.fortytwo.ripple.libs.stack;

import net.fortytwo.flow.Sink;
import net.fortytwo.ripple.RippleException;
import net.fortytwo.ripple.model.ModelConnection;
import net.fortytwo.ripple.model.PrimitiveStackMapping;
import net.fortytwo.ripple.model.RippleList;

/**
 * A filter which discards all of the stack apart from the topmost item.
 *
 * @author Joshua Shinavier (http://fortytwo.net)
 */
public class Top extends PrimitiveStackMapping {
    private static final String[] IDENTIFIERS = {
            StackLibrary.NS_2013_03 + "top"};

    public String[] getIdentifiers() {
        return IDENTIFIERS;
    }

    public Top() {
        super();
    }

    public Parameter[] getParameters() {
        return new Parameter[]{
                new Parameter("d", "item at the top of the stack which is preserved", true)};
    }

    public String getComment() {
        return "retains the topmost item on the stack and throws away the rest of the stack";
    }

    public void apply(final RippleList arg,
                      final Sink<RippleList> solutions,
                      final ModelConnection mc) throws RippleException {
        solutions.accept(mc.list().push(arg.getFirst()));
    }
}

