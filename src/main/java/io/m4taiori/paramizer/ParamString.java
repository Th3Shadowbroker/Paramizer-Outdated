package io.m4taiori.paramizer;

import io.m4taiori.paramizer.exceptions.ParamException;
import io.m4taiori.paramizer.validation.ParamValidator;

import java.util.*;

/**
 * A string that can be interpreted with parameters.
 */
public class ParamString
{

    /**
     * The original String.
     */
    private final String raw;

    /**
     * Contains unassigned parameters.
     */
    private final List<String> unassigned = new ArrayList<>();

    /**
     * Contains value-flags.
     */
    private final Map<String, String> valueFlags = new HashMap<>();

    /**
     * Contains flags.
     */
    private final List<String> flags = new ArrayList<>();

    /**
     * The validator for this param-string.
     */
    private ParamValidator validator;

    /**
     * Constructor of {@link ParamString}.
     * @param raw The string that should be interpreted.
     */
    private ParamString( String raw )
    {
        this.raw = raw;
        interpret(raw);
    }

    /**
     * Used to interpret the string.
     * @param raw The raw input
     */
    private void interpret( String raw )
    {
        String[] splitted = raw.split(" ");

        for ( int i = 0; i < splitted.length; i++ )
        {
            String arg = splitted[i];

            //Flag or value-flag.
            if ( arg.startsWith("-") )
            {
                //Optional next argument.
                Optional<String> nextArgument = Optional.ofNullable( (i + 1 < splitted.length) ? splitted[i+1] : null );

                if ( nextArgument.isPresent() )
                {
                    //Next argument is a flag.
                    if ( nextArgument.get().startsWith("-") )
                    {
                        flags.add( arg.replace("-", "") );
                        continue;
                    }

                    //Next argument is a value-flag. Skip the assigned value.
                    else
                    {
                        valueFlags.put( arg.replace("-", ""), nextArgument.get() );
                        i++;
                        continue;
                    }

                }

                flags.add(arg.replace("-", ""));
            }

            //No defined flag or value-flag.
            else
            {
                unassigned.add(arg);
            }
        }
    }

    /**
     * Make sure the ParamString contains a specific flag.
     * @param name The name of the value/parameter.
     * @return boolean
     */
    public boolean hasFlag( String name )
    {
        return flags.contains(name);
    }

    /**
     * Make sure the ParamString contains unassigned values.
     * @return boolean
     */
    public boolean hasUnassignedParameters()
    {
        return unassigned.isEmpty();
    }

    /**
     * Make sure the ParamString contains an value-flag.
     * @param name The name of the value/parameter.
     * @return boolean
     */
    public boolean hasValueFlag( String name )
    {
        return valueFlags.containsKey(name);
    }

    /**
     * Get an array of all flags.
     * @return String[]
     */
    public String[] getFlags()
    {
        return flags.toArray( new String[flags.size()] );
    }

    /**
     * Get an array of all unassigned values.
     * @return String[]
     */
    public String[] getUnassignedValues()
    {
        return unassigned.toArray( new String[unassigned.size()] );
    }

    /**
     * Get an array containg all value-flags.
     * @return String[]
     */
    public String[] getValueFlags()
    {
        return valueFlags.keySet().toArray(new String[valueFlags.size()]);
    }

    /**
     * Get the value of an specific value-flag.
     * @param value The value.
     * @return String
     */
    public String getValueFlag( String value )
    {
        return valueFlags.get(value);
    }

    /**
     * Set the validator used to validate this {@link ParamString}
     * @param validator The {@link ParamValidator} used to validate this {@link ParamString}.
     */
    public void setValidator( ParamValidator validator )
    {
        this.validator = validator;
    }

    /**
     * Get the assigned validator.
     * @return ParamValidator
     */
    public ParamValidator getValidator()
    {
        return validator;
    }

    @Override
    public String toString()
    {
        return raw;
    }

    /**
     * Validates this {@Link ParamString} with the assigned {@link ParamValidator}
     * @throws ParamException Thrown if the validation failed.
     */
    public void validate() throws ParamException
    {
        if ( validator != null ) validator.validate(this);
    }

    /**
     * Performs silent validation.
     * @return True if validation succeeded.
     */
    public boolean validateSilently()
    {
        try
        {
            validate();
            return true;
        }
        catch ( ParamException ex )
        {
            return false;
        }
    }

    /**
     * Create a param-string based on a string.
     * @param s The string.
     * @return ParamString
     */
    public static ParamString of( String s )
    {
        return new ParamString(s);
    }

    /**
     * Create a param-string based on an array.
     * @param array The array.
     * @return Param string.
     */
    public static ParamString of( String[] array )
    {
        return new ParamString( String.join(" ", array) );
    }

}
