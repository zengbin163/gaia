#It's About commons-email
##Debugging
The JavaMail API supports a debugging option that will can be very useful if you run into problems. 
You can activate debugging on any of the mail classes by calling __setDebug(true)__. 
The debugging output will be written to **System.out** .

Sometimes you want to experiment with various security setting or features of commons-email. 
A good starting point is the test class **EmailLiveTest** and **EmailConfiguration** which are used for testing commons-email with real SMTP servers.

##Authentication
If you need to authenticate to your SMTP server, you can call the __#setAuthentication(userName,password)__ method before sending your email. This will create an instance of __DefaultAuthenticator__ which will be used by the JavaMail API when the email is sent. Your server must support RFC2554 in order for this to work.

You can perform a more complex authentication method such as displaying a dialog box to the user by creating a subclass of the __javax.mail.Authenticator__ object. You will need to override the __#getPasswordAuthentication()__ method where you will handle collecting the user's information. To make use of your new __Authenticator__ class, use the __Email.setAuthenticator__ method.

##Security
Nowadays you should not use plain SMTP protocol when using public SMTP servers but there is a some confusion regarding the available options.

Two commons options are using:
+ STARTTLS on port 25
+ SSL on port 465  
    
The following definitions were taken from Wikipedia
+ STARTTLS is an extension to plain text communication protocols, which offers a way to upgrade a plain text connection to an encrypted (TLS or SSL) connection instead of using a separate port for encrypted communication.
+ Transport Layer Security (TLS) and its predecessor, Secure Sockets Layer (SSL), are cryptographic protocols that provide communication security over the Internet.TLS and SSL encrypt the segments of network connections above the Transport Layer, using asymmetric cryptography for key exchange, symmetric encryption for privacy, and message authentication codes for message integrity.

In addition you can force the following security checks (which are disabled by default)
+ When using a secured transport (STARTTLS or SSL) you can force validating the server's certificate by calling __Email.setSSLCheckServerIdentity(true)__ .
+ Enforce using STARTTLS by calling __Email.setStartTLSRequired(true)__ .

##Handling Bounced Messages
Normally, messages which cannot be delivered to a recipient are returned to the sender (specified with the __from__ property). However, in some cases, you'll want these to be sent to a different address. To do this, simply call the __setBounceAddress(emailAddressString)__ method before sending your email.

Technical notes: When SMTP servers cannot deliver mail, they do not pay any attention to the contents of the message to determine where the error notification should be sent. Rather, they refer to the SMTP "envelope sender" value. JavaMail sets this value according to the value of the __mail.smtp.from__ property on the JavaMail __Session__. (Commons Email initializes the JavaMail __Session__ using __System.getProperties())__ If this property has not been set, then JavaMail uses the "from" address. If your email bean has the __bounceAddress__ property set, then Commons Email uses it to set the value of __mail.smtp.from__ when the __Session__ is initialized, overriding any other value which might have been set.

Note: This is the only way to control the handling of bounced email. Specifically, the "Errors-to:" SMTP header is deprecated and cannot be trusted to control how a bounced message will be handled. Also note that it is considered bad practice to send email with an untrusted "from" address unless you also set the bounce address. If your application allows users to enter an address which is used as the "from" address on an email, you should be sure to set the bounce address to a known good address.