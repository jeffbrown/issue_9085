package demo

class Attribute {
    String name
    String value

    static constraints = {
        name(blank:false)
        value(blank:false)
    }

    static mapping = {
        value type: "text"
    }

    String toString(){
        return this.name + " : "+ this.value
    }
}
