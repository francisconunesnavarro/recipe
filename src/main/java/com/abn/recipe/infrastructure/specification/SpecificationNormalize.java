package com.abn.recipe.infrastructure.specification;

import java.text.Normalizer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * Normalize for Specifications.
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class SpecificationNormalize {
    
    /**
     * Characters with Accentuation.
     */
    static String CHARACTERS = "áãàâäçéèëêùûüúóôöïîíÁÀÂÄÃÇÉÈËÊÙÛÜÚÓÔÖÏÎÍ";
    /**
     * Value to replace.
     */
    static String REPLACE = "REPLACE";
    /**
     * Characters without Accentuation.
     */
    static String REPLACEMENTS = "aaaaaceeeeuuuuoooiiiAAAAACEEEEUUUUOOOIII";
    
    private SpecificationNormalize() {
    }
    
    /**
     * Normalizes a database column by removing accents.
     *
     * @param builder {@link CriteriaBuilder}
     * @param column  {@link Expression<String>} column to replace
     * @return {@link Expression<String>}
     */
    public static Expression<String> normalizeDbColumn(final CriteriaBuilder builder, Expression<String> column) {
        for (int i = 0; i < CHARACTERS.length(); i++) {
            column = builder.function(REPLACE,
                String.class,
                column,
                builder.literal(CHARACTERS.charAt(i)),
                builder.literal(REPLACEMENTS.charAt(i))
            );
        }
        
        return builder.lower(column);
    }
    
    /**
     * Normalizes a String by removing accents.
     *
     * @param str {@link String} to be normalized
     * @return {@link String} normalized
     */
    public static String normalizeValue(final String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}
