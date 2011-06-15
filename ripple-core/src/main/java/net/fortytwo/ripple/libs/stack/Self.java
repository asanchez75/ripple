/*
 * $URL$
 * $Revision$
 * $Author$
 *
 * Copyright (C) 2007-2011 Joshua Shinavier
 */


package net.fortytwo.ripple.libs.stack;

import net.fortytwo.ripple.RippleException;
import net.fortytwo.ripple.model.PrimitiveStackMapping;
import net.fortytwo.ripple.model.StackContext;
import net.fortytwo.flow.Sink;
import net.fortytwo.ripple.model.StackMapping;

/**
 * A primitive function which has no effect on the stack.
 */
public class Self extends PrimitiveStackMapping
{
    private static final String[] IDENTIFIERS = {
            StackLibrary.NS_2011_04 + "self",
            StackLibrary.NS_2008_08 + "id",
            StackLibrary.NS_2007_08 + "id",
            StackLibrary.NS_2007_05 + "id"};

    public String[] getIdentifiers()
    {
        return IDENTIFIERS;
    }

	public Self()
		throws RippleException
	{
		super();
	}

    public Parameter[] getParameters()
    {
        return new Parameter[] {};
    }

    public String getComment()
    {
        return " =>  (identity function, does nothing)";
    }

	public void apply( final StackContext arg,
						 final Sink<StackContext, RippleException> solutions )
		throws RippleException
	{
		solutions.put( arg );
	}

    @Override
    public StackMapping getInverse() {
        return this;
    }
}
