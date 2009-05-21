/*
 * $URL$
 * $Revision$
 * $Author$
 *
 * Copyright (C) 2007-2009 Joshua Shinavier
 */


package net.fortytwo.ripple.libs.etc;

import net.fortytwo.ripple.RippleException;
import net.fortytwo.ripple.flow.Sink;
import net.fortytwo.ripple.model.PrimitiveStackMapping;
import net.fortytwo.ripple.model.ModelConnection;
import net.fortytwo.ripple.model.StackContext;
import net.fortytwo.ripple.model.RippleList;

import java.lang.System;

/**
 * A primitive which produces the current time, in seconds since midnight UTC of
 * January 1, 1970.
 */
public class Time extends PrimitiveStackMapping
{
    private static final String[] IDENTIFIERS = {
            EtcLibrary.NS_2008_08 + "time",
            EtcLibrary.NS_2007_08 + "time",
            EtcLibrary.NS_2007_05 + "time"};

    public String[] getIdentifiers()
    {
        return IDENTIFIERS;
    }

    public Parameter[] getParameters()
    {
        return new Parameter[] {};
    }

    public String getComment()
    {
        return "=>  t  -- pushes the current time, in milliseconds since the Unix epoch";
    }

	public Time() throws RippleException
	{
		super();
	}

	public void apply( final StackContext arg,
						 final Sink<StackContext, RippleException> solutions	)
		    throws RippleException
	{
		final ModelConnection mc = arg.getModelConnection();
		RippleList stack = arg.getStack();

		solutions.put( arg.with( stack.push(
			mc.value( System.currentTimeMillis() ) ) ) );
	}
}
