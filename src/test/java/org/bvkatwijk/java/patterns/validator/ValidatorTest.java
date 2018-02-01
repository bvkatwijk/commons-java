package org.bvkatwijk.java.patterns.validator;

import java.util.Arrays;

import org.bvkatwijk.commons.validator.Validator;
import org.bvkatwijk.commons.validator.Validator.Validation;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for {@link Validator}
 * 
 * @author bvkatwijk
 *
 */
public class ValidatorTest {

	public static class SingleValidation {

		private static final String ERROR_MESSAGE = "not valid";
		private static final Validator<String> VALIDATOR = new Validator<>(Arrays.asList(
				new Validation<>("valid"::equals, ERROR_MESSAGE)));

		@Test
		public void stringValidator_onValidString_shouldBeValid() {
			Assert.assertTrue(VALIDATOR.test("valid").isValid());
		}

		@Test
		public void stringValidator_onValidString_shouldProvideMessage() {
			Assert.assertNotEquals(ERROR_MESSAGE, VALIDATOR.test("valid").getMessage());
		}

		@Test
		public void stringValidator_onInvalidString_shouldNotBeValid() {
			Assert.assertFalse(VALIDATOR.test("invalid").isValid());
		}

		@Test
		public void stringValidator_onInvalidString_shouldProvideMessage() {
			Assert.assertEquals(ERROR_MESSAGE, VALIDATOR.test("invalid").getMessage());
		}

		@Test
		public void stringValidator_onEmptyString_shouldNotBeValid() {
			Assert.assertFalse(VALIDATOR.test("").isValid());
		}

		@Test
		public void stringValidator_onNullString_shouldNotBeValid() {
			Assert.assertFalse(VALIDATOR.test(null).isValid());
		}

	}

	public static class MultipleValidations {

		private static final String ERROR_MESSAGE_1 = "More than 9";
		private static final String ERROR_MESSAGE_2 = "Less than 1";
		private static final Validator<Integer> POSITIVE_SINGLE_DIGIT = new Validator<>(Arrays.asList(
				new Validation<>(value -> value <= 9, ERROR_MESSAGE_1),
				new Validation<>(value -> value >= 1, ERROR_MESSAGE_2)));

		@Test
		public void intValidator_onFive_shouldBeValid() {
			Assert.assertTrue(POSITIVE_SINGLE_DIGIT.test(5).isValid());
		}

		@Test
		public void intValidator_onOne_shouldBeValid() {
			Assert.assertTrue(POSITIVE_SINGLE_DIGIT.test(1).isValid());
		}

		@Test
		public void intValidator_onZero_shouldNotBeValid() {
			Assert.assertFalse(POSITIVE_SINGLE_DIGIT.test(0).isValid());
		}

	}

}
