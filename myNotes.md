## What have I finished

* Implement a create gadget endpoint in the controller layer.
* Implement a find all gadgets endpoint in the controller layer.
* Return associated gadgets with widgets in the controller layer.
* Rather than returning a list of widgets, we would like to return a paged response from the find all widgets endpoint.
* Optionally write tests for the above tasks.

### Notes

* In order to create the gadget entity two things are required: name and widget's id. Since widget id was not provided in the CreateGadgetRequest I decided to assign each gadget some random widget if it exists. If there are no widgets at all, then widgetId will be randomly generated.

* To check if endpoints were working was I using the tool called [Postman](https://www.getpostman.com/)