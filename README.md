Exercise for the tutorial "Learn You a Kotlin For All The Good It Will Do You"
==============================================================================

First create a new branch. Check in after each change.  This lets you
easily show how auto-converting code to Kotlin affects how its API
looks when used from Java

Suggested progress

* Auto-convert from Java to Kotlin & clean-up
  * Presenter
    * convert to a data class
  * Session
    * convert to a data class
      * subtitle is a `String?`
    * No need to wrap List in unmodifiableList: discuss List/MutableList split
    * move withXxx methods out of class into extension methods
  * Json
    * move functions to module scope
    * use nullable types to indicate that array *elements* can be null
  * JsonFormat
    * move functions to module scope
    * convert to extension methods on domain types and JsonNode

* Address loss of type safety w.r.t. exceptions
  * Make SessionCode::parse return a SessionCode?
  * Introduce a Result<T> algebraic data type (sealed class hierarchy)
  * Make JsonNode.toXxx return Result<Xxx>
  * Introduce operations on Result<T>:
    * map :: (A)->B, Result<A> -> Result<B>
    * flatMap :: (A)->Result<B>, Result<A> -> Result<B>
    * all :: (List<Result<T>>) -> Result<List<T>>
    * apply :: ((A,B,C)->R, Result<A>, Result<B>, Result<C>)->Result<R>
