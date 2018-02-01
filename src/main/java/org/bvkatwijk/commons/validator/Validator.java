package org.bvkatwijk.commons.validator;

import java.util.List;
import java.util.function.Predicate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author bvkatwijk
 *
 * @param <T>
 *            Type to validate
 */
@RequiredArgsConstructor
public class Validator<T> {

	private final List<Validation<T>> validations;

	/**
	 * @param entity
	 *            to be validated
	 * @return ValidationResult
	 */
	public ValidationResult test(T entity) {
		return validations.stream()
				.filter(violatedBy(entity))
				.findFirst()
				.map(this::toResult)
				.orElse(ValidationResult.valid());
	}

	private Predicate<? super Validation<T>> violatedBy(T entity) {
		return validation -> validation.getPredicate().negate().test(entity);
	}

	private ValidationResult toResult(Validation<T> validation) {
		return new ValidationResult(false, validation.getErrorMessage());
	}

	public T requireValid(T entity) {
		ValidationResult result = test(entity);
		if (!result.isValid()) {
			throw new IllegalArgumentException(result.getMessage());
		}
		return entity;
	}

	@Getter
	@RequiredArgsConstructor
	public static class Validation<T> {

		private final Predicate<T> predicate;
		private final String errorMessage;

	}

	/**
	 * Represenation of result after validation
	 */
	@Getter
	@RequiredArgsConstructor
	public static class ValidationResult {

		private final boolean isValid;
		private final String message;

		private static ValidationResult valid() {
			return new ValidationResult(true, "Supplied entity was valid.");
		}

	}

}
