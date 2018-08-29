package io.m4taiori.paramizer.validation;

import io.m4taiori.paramizer.ParamString;
import io.m4taiori.paramizer.exceptions.ParamException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamValidator
{

    /**
     * A list containing required value-flags.
     */
    private final List<String> requiredValueFlags = new ArrayList<>();

    /**
     * A list containing optional value-flags.
     */
    private final List<String> optionalValueFlags = new ArrayList<>();

    /**
     * A list containing required flags.
     */
    private final List<String> requiredFlags = new ArrayList<>();

    /**
     * A list containing optional flags.
     */
    private final List<String> optionalFlags = new ArrayList<>();

    /**
     * The minimal amount of unassigned parameters required for validation.
     */
    private int minimalUnassignedParameters = -1;

    /**
     * Set the minimal amount of unassigned parameters.
      * @param min The minimal amount of unassigned parameters.
     * @return ParamValidator
     */
    public ParamValidator minimalUnassigend( int min )
    {
        this.minimalUnassignedParameters = min;
        return this;
    }

    /**
     * Add an required value-flag.
     * @param flag The name of the flag.
     * @return ParamValidator
     */
    public ParamValidator requireValueFlag( String flag )
    {
        if (!requiredValueFlags.contains(flag)) requiredValueFlags.add(flag);
        return this;
    }

    /**
     * Add an optional value-flag.
     * @param flag The name of the flag.
     * @return ParamValidator
     */
    public ParamValidator optionalValueFlag( String flag )
    {
        if (!optionalValueFlags.contains(flag)) optionalValueFlags.add(flag);
        return this;
    }

    /**
     * Add a required flag.
     * @param flag The name of the flag.
     * @return ParamValidator
     */
    public ParamValidator requireFlag( String flag )
    {
        if (!requiredFlags.contains(flag)) requiredFlags.add(flag);
        return this;
    }

    /**
     * Add a optional flag.
     * @param flag The name of the flag.
     * @return ParamValidator
     */
    public ParamValidator optionalFlag( String flag )
    {
        if (!optionalFlags.contains(flag)) optionalFlags.add(flag);
        return this;
    }

    /**
     * Validates a pstring with given credentials.
     * @param pstring The {@link ParamString}
     * @throws ParamException Thrown if the validation was unsuccessful.
     */
    public void validate(ParamString pstring) throws ParamException
    {
        //Lists and map to store errors.
        List<String> missingValueFlags = new ArrayList<>();
        List<String> invalidValueFlags = new ArrayList<>();
        List<String> missingFlags = new ArrayList<>();
        List<String> invalidFlags = new ArrayList<>();

        //Check for missing or invalid value-flags.
        for ( String flag : requiredValueFlags )
        {
            //Missing flag ?
            if ( !pstring.hasValueFlag(flag) ) missingValueFlags.add(flag);

            //Flag exists but as flag, not as value-flag.
            if ( pstring.hasFlag(flag) ) invalidValueFlags.add(flag);
        }

        //Check for invalid optional value-flags.
        for ( String flag : optionalValueFlags )
        {
            //Flag exists but as flag, not as value-flag.
            if ( !pstring.hasValueFlag(flag) && pstring.hasFlag(flag) ) invalidValueFlags.add(flag);
        }

        //Check for missing or invalid flags.
        for ( String flag : requiredFlags )
        {
            //Missing flag ?
            if ( !pstring.hasFlag(flag) ) missingFlags.add(flag);

            //Flag exists but as flag, not as value-flag.
            if ( pstring.hasValueFlag(flag) ) invalidFlags.add(flag);
        }

        //Check for invalid optional flags.
        for ( String flag : optionalFlags )
        {
            //Flag exists but as flag, not as value-flag.
            if ( !pstring.hasFlag(flag) && pstring.hasValueFlag(flag) ) invalidFlags.add(flag);

        }

        //Check for validation errors.
        if
        (
                missingValueFlags.size() > 0 ||
                missingFlags.size() > 0 ||
                invalidValueFlags.size() > 0 ||
                invalidFlags.size() > 0 ||
                pstring.getUnassignedValues().length < minimalUnassignedParameters
        )
        {
            throw new ParamException
                    (
                            missingValueFlags.toArray(new String[missingValueFlags.size()]),
                            invalidValueFlags.toArray(new String[invalidValueFlags.size()]),

                            missingFlags.toArray(new String[missingFlags.size()]),
                            invalidFlags.toArray(new String[invalidFlags.size()]),

                            minimalUnassignedParameters,
                            pstring
                    );
        }

    }

}
