struts-cdi
==========
CDI support for Apache Struts 1.x


Foreword
--------
I do not in any means support starting a project using the Struts 1.x framework. It is heavily 
outdated, and is officially unmaintained since September 21, 2013 when the Apache Software 
foundation announced that 
**the Struts 1.x web framework has reached its end of life and is no longer officially supported.**


What is struts-cdi
-------------
This is a java library for Struts 1.x that enables @Inject style dependency injection (DI) and the 
usage of contextual instances supported by the JSR-299 (a.k.a. CDI) specification.

So basically it is **CDI support for struts actions**. On Struts actions you can use the various 
styles of DI (constructor, setter and field), interceptors, decorators and events.

The library is compatible with struts versions 1.2.9 and 1.3.10. 
I have not tried other versions. If if works, it works.
I do not intend to provide support for versions earlier than the given ones.


How to use it
----------------
**struts < 1.3**

use struts.cdi.CdiRequestProcessor or struts.cdi.TilesCdiRequestProcessor.

You can do this by adding the following to your struts-config.xml:

        <controller nocache="true"processorClass="struts.cdi.CdiRequestProcessor">
        </controller>
        
**struts >= 1.3**

You can still use the previously described request processors. Struts 1.3 introduced the
chain of responsibility pattern, so there is a simpler alternative.

In web.xml add this init parameter to the struts servlet:

        <init-param>
            <param-name>chainConfig</param-name>
            <param-value>struts/cdi/chain-config.xml</param-value>
        </init-param>
        
If you already use a custom chain configuration, add the following command to your custom 
chain-config.xml configuration instead replacing the CreateAction command:
        
        <command className="struts.cdi.CreateCdiAction"/>

The Action creation only works, if a CDI BeanManager is available at either of the following 
JNDI locations:

        java:comp/BeanManager 
        java:comp/env/BeanManager
         
This could be provided by your application (by bundling a CDI implementation with your app) 
or by the container that the application is deployed to.

Will ActionForms be supported as well?
--------------------------------------
I will only try to add support for CDI in ActionForms if the need arises for it.


Why I did this for a product that already reached EOL
-------------------------------------------------------------------------------------
I wanted to learn how to use the CDI BeanManager and it seemed like a good 
exercise to integrate CDI into an old framework that does not have CDI support yet. 

Struts2 already has CDI support, but I didn't find any for Struts1. 
Even though it was EOL, I think it is very widespread in the enterprise. A lot of places are still 
using it because they cannot deal with that size of technological debt in a short amount of time.

The goal of this library is not to encourage the usage of outdated software, but to give a helping
hand in trying to migrate away from it. And the best thing for that in my opinion in the case of
Struts1 is CDI. This library could help people finally start to deal with their tehnological debt 
and let Struts1 die peacefully.