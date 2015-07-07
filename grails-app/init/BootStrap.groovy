class BootStrap {

    def init = { servletContext ->
        new demo.Attribute(name: 'One', value: '001').save()
        new demo.Attribute(name: 'Two', value: '002').save()
        new demo.Attribute(name: 'Three', value: '003').save()
    }
    def destroy = {
    }
}
