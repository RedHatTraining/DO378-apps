import React, { useState } from "react";
import { StarIcon, OutlinedStarIcon } from "@patternfly/react-icons";

export function StarRating(props: {
    rating: number;
    max: number;
    onRatingChange?: (rating: number) => void;
}) {
    const [ratingState, setRatingState] = useState<number>(props.rating);

    // create a list of rating values
    const ratings = [];
    for (let i = 1; i < props.max + 1; i++) {
        ratings.push(i);
    }

    return (
        <>
            {ratings.map((starRating: number) => {
                return (
                    <button
                        key={`star-${starRating}`}
                        style={{
                            color: "goldenrod",
                            border: "none",
                            backgroundColor: "transparent",
                        }}
                        onMouseEnter={() => setRatingState(starRating)}
                        onMouseLeave={() => setRatingState(props.rating)}
                        onClick={() => {
                            props.onRatingChange?.(starRating);
                        }}
                    >
                        {starRating <= ratingState ? (
                            <StarIcon />
                        ) : (
                            <OutlinedStarIcon />
                        )}
                    </button>
                );
            })}
        </>
    );
}
