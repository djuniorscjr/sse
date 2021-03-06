package util

import play.twirl.api.Html
import views.html.helper.{FieldElements, FieldConstructor}
import views.html.materializecss._

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
        implicit val date = new FieldConstructor {
          override def apply(elts: FieldElements): Html = dataInput(elts)
        }
        implicit val radio = new FieldConstructor {
          override def apply(elts: FieldElements): Html = radioInput(elts)
        }
}
