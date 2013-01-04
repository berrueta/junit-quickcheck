/*
 The MIT License

 Copyright (c) 2010-2012 Paul R. Holser, Jr.

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.pholser.junit.quickcheck.generator;

import com.pholser.junit.quickcheck.random.SourceOfRandomness;

/**
 * Produces values for {@code enum} theory parameters.
 */
public class EnumGenerator extends Generator<Enum> {
    private final Class<?> enumType;
    private ValuesOf turnOffRandomness;

    /**
     * Makes a new generator for an {@code enum} of a specific type.
     *
     * @param enumType the type of the {@code enum} to be generated
     */
    public EnumGenerator(Class<?> enumType) {
        super(Enum.class);

        this.enumType = enumType;
    }

    /**
     * <p>Tells this generator to generate the values of the {@code enum} in turn on successive requests.</p>
     *
     * <p>Without this configuration, the values of the {@code enum} are are generated with approximately equal
     * probability.</p>
     *
     * @param flag annotation to turn off random generation and replace it with values that cycle through the
     * {@code enum}
     */
    public void configure(ValuesOf flag) {
        turnOffRandomness = flag;
    }

    @Override
    public Enum<?> generate(SourceOfRandomness random, GenerationStatus status) {
        Object[] values = enumType.getEnumConstants();
        int index = turnOffRandomness == null
            ? random.nextInt(0, values.length - 1)
            : status.attempts() % values.length;
        return (Enum<?>) values[index];
    }
}