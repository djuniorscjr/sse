package util

import play.twirl.api.Html
import views.html.helper.{FieldElements, FieldConstructor}
import views.html.materializecss.{passwordInput, textInput, textAreaInput}

/**
 * Created by Domingos Junior on 09/06/2015.
 */
object Materialize {
        implicit val text = new FieldConstructor {
          override def apply(elts: FieldElements): Html = textInput(elts)
        }
        implicit val password = new FieldConstructor {
          override def apply(elts: FieldElements): Html = passwordInput(elts)
        }
        implicit val area = new FieldConstructor {
          override def apply(elts: FieldElements): Html = textAreaInput(elts)
        }
}
