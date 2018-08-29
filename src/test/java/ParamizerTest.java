import io.m4taiori.paramizer.ParamString;
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


    }

}
