package demo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AttributeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Attribute.list(params), model:[attributeCount: Attribute.count()]
    }

    def show(Attribute attribute) {
        respond attribute
    }

    def create() {
        respond new Attribute(params)
    }

    @Transactional
    def save(Attribute attribute) {
        if (attribute == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (attribute.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond attribute.errors, view:'create'
            return
        }

        attribute.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'attribute.label', default: 'Attribute'), attribute.id])
                redirect attribute
            }
            '*' { respond attribute, [status: CREATED] }
        }
    }

    def edit(Attribute attribute) {
        respond attribute
    }

    @Transactional
    def update(Attribute attribute) {
        if (attribute == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (attribute.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond attribute.errors, view:'edit'
            return
        }

        attribute.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'attribute.label', default: 'Attribute'), attribute.id])
                redirect attribute
            }
            '*'{ respond attribute, [status: OK] }
        }
    }

    @Transactional
    def delete(Attribute attribute) {

        if (attribute == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        attribute.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'attribute.label', default: 'Attribute'), attribute.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'attribute.label', default: 'Attribute'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
