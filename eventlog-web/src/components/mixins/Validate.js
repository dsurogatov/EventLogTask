export const Validator = {
  computed: {
    isFormInvalid() {
      return Object.keys(this.formFields).some(key => this.formFields[key].invalid)
    }
  }
}
