package com.microsoft.bingads.v10.api.test.entities.ad_group_negative_dsa_target.read;

import com.microsoft.bingads.internal.functionalinterfaces.Function;
import com.microsoft.bingads.v10.api.test.entities.ad_group_negative_dsa_target.BulkAdGroupNegativeDynamicSearchAdTargetTest;
import com.microsoft.bingads.v10.bulk.entities.BulkAdGroupNegativeDynamicSearchAdTarget;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BulkAdGroupNegativeDynamicSearchAdTargetReadIdTest extends BulkAdGroupNegativeDynamicSearchAdTargetTest {

    @Parameter(value = 1)
    public Long expectedResult;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
                        {"123", 123L},
                        {"9223372036854775807", 9223372036854775807L},
                        {"", null},
                        {null, null}
                }
        );
    }

    @Test
    public void testRead() {
        testReadProperty(
                "Id",
                datum,
                expectedResult,
                new Function<BulkAdGroupNegativeDynamicSearchAdTarget, Long>() {
                    @Override
                    public Long apply(BulkAdGroupNegativeDynamicSearchAdTarget c) {
                        return c.getAdGroupCriterion().getId();
                    }
                }
        );
    }
}
