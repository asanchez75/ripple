package net.fortytwo.ripple.io;

import net.fortytwo.flow.Sink;
import net.fortytwo.flow.rdf.RDFSink;
import net.fortytwo.ripple.Ripple;
import net.fortytwo.ripple.RippleException;
import net.fortytwo.ripple.model.ModelConnection;
import org.openrdf.model.Namespace;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;

// TODO: change this class to use a SailConnection instead of a ModelConnection

/**
 * @author Joshua Shinavier (http://fortytwo.net)
 */
public class RDFImporter implements RDFSink {
    private final Sink<Statement> stSink;
    private final Sink<Namespace> nsSink;
    private final Sink<String> cmtSink;

    public RDFImporter(final ModelConnection mc,
                       final Resource... contexts) throws RippleException {
        final boolean override = Ripple.getConfiguration().getBoolean(Ripple.PREFER_NEWEST_NAMESPACE_DEFINITIONS);

        stSink = new Sink<Statement>() {
            public void accept(final Statement st) throws RippleException {
//System.out.println( "adding statement: " + st );
                if (0 == contexts.length) {
                    mc.add(st.getSubject(),
                            st.getPredicate(),
                            st.getObject());
                } else {
                    for (Resource c : contexts) {
                        mc.add(st.getSubject(),
                                st.getPredicate(),
                                st.getObject(),
                                c);
                    }
                }
            }
        };

        nsSink = new Sink<Namespace>() {
            public void accept(final Namespace ns) throws RippleException {
                mc.setNamespace(ns.getPrefix(), ns.getName(), override);
            }
        };

        cmtSink = new Sink<String>() {
            public void accept(final String comment) throws RippleException {
            }
        };
    }

    public Sink<Statement> statementSink() {
        return stSink;
    }

    public Sink<Namespace> namespaceSink() {
        return nsSink;
    }

    public Sink<String> commentSink() {
        return cmtSink;
    }
}

