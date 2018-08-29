package io.m4taiori.paramizer.exceptions;

import io.m4taiori.paramizer.ParamString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An exception thrown by {@link io.m4taiori.paramizer.validation.ParamValidator}
 */
public class ParamException extends Exception
{

    /**
     * An array of missing value-flags.
     */
    private final String[] missingValueFlags;

    /**
     * An array of invalid value-flags.
     */
    private final String[] invalidValueFlags;

    /**
     * An array of missing flags.
     */
    private final String[] missingFlags;

    /**
     * An array of invalid flags.
     */
    private final String[] invalidFlags;

    /**
     * The ParamString the exception was thrown by.
     */
    private final ParamString input;

    /**
     * The minimal amount of minimal unassigned parameters.
     */
    private final int minimalUnassignedParameters;

    /**
     * Construction of {@link ParamException}
     * @param missingValueFlags An array of missing arguments.
     * @param invalidValueFlags An array of invalid arguments.
     * @param input The ParamString the exception was thrown by.
     */
    public ParamException(String[] missingValueFlags, String[] invalidValueFlags, String[] missingFlags, String[] invalidFlags, int minimalUnassignedParameters, ParamString input )
    {
        this.missingValueFlags = missingValueFlags;
        this.invalidValueFlags = invalidValueFlags;
        this.missingFlags = missingFlags;
        this.invalidFlags = invalidFlags;
        this.minimalUnassignedParameters = minimalUnassignedParameters;
        this.input = input;
    }

    /**
     * Get an array containing all missing arguments.
     * @return String[]
     */
    public String[] getMissingValueFlags()
    {
        return missingValueFlags;
    }

    /**
     * Get an array containing all invalid arguments.
     * @return String[]
     */
    public String[] getInvalidValueFlags()
    {
        return invalidValueFlags;
    }

    /**
     * Get an array containing all missing flags.
     * @return String[]
     */
    public String[] getMissingFlags()
    {
        return missingFlags;
    }

    /**
     * Get an array containing all invalid flags.
     * @return String[]
     */
    public String[] getInvalidFlags()
    {
        return invalidFlags;
    }

    /**
     * Get the ParamString the exception was thrown by.
     * @return ParamString
     */
    public ParamString getInput()
    {
        return input;
    }

    /**
     * Get the minimal amount of unassigned parameters;
     * @return int
     */
    public int getMinimalUnassignedParameters()
    {
        return minimalUnassignedParameters;
    }
}
