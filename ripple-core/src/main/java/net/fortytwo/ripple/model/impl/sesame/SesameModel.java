/*
 * $URL$
 * $Revision$
 * $Author$
 *
 * Copyright (C) 2007-2011 Joshua Shinavier
 */


package net.fortytwo.ripple.model.impl.sesame;

import net.fortytwo.flow.rdf.diff.RDFDiffSink;
import net.fortytwo.ripple.Ripple;
import net.fortytwo.ripple.RippleException;
import net.fortytwo.ripple.model.LibraryLoader;
import net.fortytwo.ripple.model.Model;
import net.fortytwo.ripple.model.ModelConnection;
import net.fortytwo.ripple.model.Operator;
import net.fortytwo.ripple.model.SpecialValueMap;
import net.fortytwo.ripple.sail.RippleSail;
import org.apache.log4j.Logger;
import org.openrdf.sail.Sail;
import org.openrdf.sail.SailException;

import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A <code>Model</code> implementation using the Sesame RDF toolkit.
 */
public class SesameModel implements Model {
    private static final Logger LOGGER = Logger.getLogger(SesameModel.class);

    final Sail sail;
    //final RippleSail rippleSail;
    SpecialValueMap specialValues;
    final Set<ModelConnection> openConnections = new LinkedHashSet<ModelConnection>();

    public SesameModel(final Sail baseSail) throws RippleException {
        this(baseSail, Ripple.class.getResource("libraries.txt"));
    }

    public SesameModel(final Sail baseSail,
                       final URL libraries)
            throws RippleException {
        LOGGER.debug("Creating new SesameModel");

        sail = baseSail;

        /*
        rippleSail = new RippleSail(this);
        // FIXME: for now, this completely disables asynchronous query answering
        try {
            rippleSail.initialize();
        } catch (SailException e) {
            throw new RippleException(e);
        }*/

        ModelConnection mc = createConnection();

        try {
            // TODO: eliminate this temporary value map
            specialValues = new SpecialValueMap();
            specialValues = new LibraryLoader().load(libraries, mc);

            // At the moment, op needs to be a special value for the sake of the
            // evaluator.  This has the side-effect of making "op" a keyword.
            specialValues.add(Operator.OP, mc);

            // The nil list also needs to be special, so "nil" is also incidentally a keyword.
            specialValues.add(mc.list(), mc);
        } finally {
            mc.close();
        }
    }

    public SpecialValueMap getSpecialValues() {
        return specialValues;
    }

    public ModelConnection createConnection()
            throws RippleException {
        return new SesameModelConnection(this, null);
    }

    public ModelConnection createConnection(final RDFDiffSink listener) throws RippleException {
        return new SesameModelConnection(this, listener);
    }

    public void shutDown() throws RippleException {
        // Warn of any open connections, then close them
        synchronized (openConnections) {
            if (openConnections.size() > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(openConnections.size()).append(" dangling connections: \"");
                boolean first = true;
                for (ModelConnection mc : openConnections) {
                    if (first) {
                        first = false;
                    } else {
                        sb.append(",");
                    }

                    sb.append(mc);
                }

                LOGGER.warn(sb.toString());

                for (ModelConnection mc : openConnections) {
                    mc.close();
                }
            }
        }
    }

    // Note: this method is not in the Model API
    public Sail getSail() {
        return sail;
    }
}