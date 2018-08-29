import io.m4taiori.paramizer.ParamString;
import io.m4taiori.paramizer.exceptions.ParamException;
import io.m4taiori.paramizer.validation.ParamValidator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ParamizerTest
{

    @Test
    public void test()
    {
        ParamString pstring = ParamString.of( "unassigned -a assigned-value -flag --help nohelp" );

        System.out.println
                (
                        "Testing String: " + pstring + "\n\n" +
                        "Unassigned values(" + pstring.getUnassignedValues().length + "): " + Arrays.toString(pstring.getUnassignedValues()) + "\n" +
                        "Assigned values (" + pstring.getValueFlags().length + "): " + Arrays.toString(pstring.getValueFlags()) + "\n" +
                        "Flags (" + pstring.getFlags().length + "): " + Arrays.toString(pstring.getFlags()) + "\n" +
                        "Assignment-Map:"
                );

        for( String key : pstring.getValueFlags() )
        {
            String s = key + " => " + pstring.getValueFlag(key);
            System.out.println("\t" + s);
        }

        System.out.println( "\nStarting testing of the ParamValidator class..." );

        pstring.setValidator
                (
                        new ParamValidator()
                            .requireFlag("flag")
                            .requireValueFlag("help")
                );


        System.out.println( "Silent validation: " + (pstring.validateSilently() ? "Validation successful!" : "Validation failed!" ));
        Assert.assertTrue(pstring.validateSilently());

        System.out.println( "\nExpecting error for required flag \"aflag\" and a minimal amount of 3 unassigned parameters." );

        pstring.setValidator
                (
                        new ParamValidator()
                                .requireFlag( "aflag" )
                                .minimalUnassigend(3)
                );

        try
        {
            pstring.validate();
        } catch (Exception ex) {
            Assert.assertTrue( ex instanceof  ParamException && ((ParamException) ex).getMinimalUnassignedParameters() > pstring.getUnassignedValues().length );
        }

    }

}
