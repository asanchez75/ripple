/*
 * $URL$
 * $Revision$
 * $Author$
 *
 * Copyright (C) 2007-2009 Joshua Shinavier
 */


package net.fortytwo.ripple.libs.math;

import net.fortytwo.ripple.RippleException;
import net.fortytwo.flow.Sink;
import net.fortytwo.ripple.model.ModelConnection;
import net.fortytwo.ripple.model.NumericValue;
import net.fortytwo.ripple.model.PrimitiveStackMapping;
import net.fortytwo.ripple.model.RippleList;
import net.fortytwo.ripple.model.StackContext;
import net.fortytwo.ripple.model.StackMapping;

/**
 * A primitive which consumes two numbers and produces their difference.
 */
public class Sub extends PrimitiveStackMapping
{
    private static final String[] IDENTIFIERS = {
            MathLibrary.NS_2008_08 + "sub",
            MathLibrary.NS_2007_08 + "sub",
            MathLibrary.NS_2007_05 + "sub"};

    public String[] getIdentifiers()
    {
        return IDENTIFIERS;
    }

	public Sub()
		throws RippleException
	{
		super();
	}

    public Parameter[] getParameters()
    {
        return new Parameter[] {
                new Parameter( "x", null, true ),
                new Parameter( "y", null, true )};
    }

    public String getComment()
    {
        return "x y  =>  x - y";
    }

	public void apply( final StackContext arg,
						 final Sink<StackContext, RippleException> solutions )
		throws RippleException
	{
		final ModelConnection mc = arg.getModelConnection();
		RippleList stack = arg.getStack();

		NumericValue a, b, result;

		b = mc.toNumericValue( stack.getFirst() );
		stack = stack.getRest();
		a = mc.toNumericValue( stack.getFirst() );
		stack = stack.getRest();

		result = a.sub( b );

		solutions.put( arg.with(
				stack.push( result ) ) );	}

    @Override
    public StackMapping inverse() throws RippleException
    {
        return MathLibrary.getAddValue();
    }
}

