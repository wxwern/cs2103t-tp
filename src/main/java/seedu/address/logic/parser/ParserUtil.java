package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.Position;
import seedu.address.model.contact.Status;
import seedu.address.model.contact.Url;
import seedu.address.model.jobapplication.ApplicationStage;
import seedu.address.model.jobapplication.Deadline;
import seedu.address.model.jobapplication.JobDescription;
import seedu.address.model.jobapplication.JobStatus;
import seedu.address.model.jobapplication.JobTitle;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String id} into an {@code Id} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code id} is invalid.
     */
    public static Id parseId(String id) throws ParseException {
        requireNonNull(id);
        String trimmedId = id.trim();
        if (!Id.isValidId(id)) {
            throw new ParseException(Id.MESSAGE_CONSTRAINTS);
        }
        return new Id(trimmedId);
    }

    /**
     * Parses a {@code String id} into a {@code Id} or {@code Index}, depending on the format of the string.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @return {@link Object} that is either an {@link Id} or an {@link Index} instance.
     * @throws ParseException if the given {@code str} is neither an index nor an id.
     */
    public static Object parseIndexXorId(String str) throws ParseException {
        String trimmedStr = str.trim();
        Object result;
        if (trimmedStr.matches("^[0-9]*$")) {
            result = ParserUtil.parseIndex(trimmedStr);
        } else {
            result = ParserUtil.parseId(trimmedStr);
        }
        return result;
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String status} into a {@code Status}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid.
     */
    public static Status parseStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        if (!Status.isValidStatus(status)) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }
        return new Status(trimmedStatus);
    }

    /**
     * Parses a {@code String position} into a {@code Position}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code position} is invalid.
     */
    public static Position parsePosition(String position) throws ParseException {
        requireNonNull(position);
        String trimmedPosition = position.trim();
        if (!Position.isValidPosition(position)) {
            throw new ParseException(Position.MESSAGE_CONSTRAINTS);
        }
        return new Position(trimmedPosition);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String url} into an {@code Url}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code url} is invalid.
     */
    public static Url parseUrl(String url) throws ParseException {
        requireNonNull(url);
        String trimmedUrl = url.trim();
        if (!Url.isValidUrl(trimmedUrl)) {
            throw new ParseException(Url.MESSAGE_CONSTRAINTS);
        }
        return new Url(trimmedUrl);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code Collection<String> ids} into a {@code Set<Tag>}.
     */
    public static Set<Id> parseIds(Collection<String> ids) throws ParseException {
        requireNonNull(ids);
        final Set<Id> idSet = new HashSet<>();
        for (String idName : ids) {
            idSet.add(parseId(idName));
        }
        return idSet;
    }


    /**
     * Parses {@code String deadline} into a {@code Deadline}.
     */
    public static Deadline parseDeadline(String deadline) throws ParseException {
        requireNonNull(deadline);
        String trimmedDeadline = deadline.trim();
        if (!Deadline.isValidDeadline(trimmedDeadline)) {
            throw new ParseException(Deadline.MESSAGE_CONSTRAINTS);
        }
        return new Deadline(trimmedDeadline);
    }

    /**
     * Parses {@code String jobTitle} into a {@code JobTitle}.
     */
    public static JobTitle parseJobTitle(String jobtitle) throws ParseException {
        requireNonNull(jobtitle);
        String trimmedTitle = jobtitle.trim();
        if (!JobTitle.isValidJobTitle(trimmedTitle)) {
            throw new ParseException(JobTitle.MESSAGE_CONSTRAINTS);
        }
        return new JobTitle(trimmedTitle);
    }

    /**
     * Parses {@code String jobStatus} into a {@code JobStatus}.
     */
    public static JobStatus parseJobStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        if (!JobStatus.isValidJobStatus(trimmedStatus)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return JobStatus.fromString(trimmedStatus);
    }

    /**
     * Parses {@code String applicationStage} into a {@code ApplicationStage}.
     */
    public static ApplicationStage parseApplicationStage(String applicationStage) throws ParseException {
        requireNonNull(applicationStage);
        String trimmedDeadline = applicationStage.trim();
        if (!ApplicationStage.isValidApplicationStage(trimmedDeadline)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return ApplicationStage.fromString(trimmedDeadline);
    }

    /**
     * Parses {@code String jobDescription} into a {@code JobDescription}
     */
    public static JobDescription parseJobDescription(String jobDescription) throws ParseException {
        requireNonNull(jobDescription);
        String trimmedDescription = jobDescription.trim();
        if (!JobDescription.isValidJobDescription(trimmedDescription)) {
            throw new ParseException(JobDescription.MESSAGE_CONSTRAINTS);
        }
        return new JobDescription(trimmedDescription);
    }

    /**
     * References a function that parses a string into an expected output within the {@link ParserUtil} utility class.
     * @param <R> The return result.
     */
    @FunctionalInterface
    public interface StringParserFunction<R> {
        R parse(String value) throws ParseException;
    }

    /**
     * Returns an object of type R that is given by passing the given string into {@code parseFunction} if
     * {@code optionalString} is non-empty, otherwise returns null.
     *
     * @param <R> The type of object returned by parsing the optionalString.
     *
     * @throws ParseException if the given {@code optionalString} is invalid as determined by {@code parseFunction}
     */
    public static <R> R parseOptionally(Optional<String> optionalString, StringParserFunction<R> parseFunction)
            throws ParseException {

        if (optionalString.isPresent()) {
            return parseFunction.parse(optionalString.get());
        }
        return null;
    }
}
